import { useState } from 'react';
import { Alert, AlertDescription } from '@/components/ui/alert';
import InputModal from '@/components/InputModal';
import API_ENDPOINTS from '@/config/api';
import { Download } from 'lucide-react';

const ImageEditor = () => {
  const [image, setImage] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [preview, setPreview] = useState(null);
  const [processedImage, setProcessedImage] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [modalConfig, setModalConfig] = useState({
    title: '',
    min: 0,
    max: 0,
    onSubmit: () => {},
  });

  // Add image compression before upload
  const compressImage = async (file) => {
    const options = {
      maxSizeMB: 1,
      maxWidthOrHeight: 1920
    }
    try {
      return await imageCompression(file, options);
    } catch (error) {
      console.error(error);
      return file;
    }
  }

  // Modify handleImageUpload
  const handleImageUpload = async (e) => {
    const file = e.target.files[0];
    if (file) {
      const compressedFile = await compressImage(file);
      setImage(compressedFile);
      setPreview(URL.createObjectURL(compressedFile));
      setProcessedImage(null);
    }
  };

  const handleProcess = async (operation, value = null) => {
    if (!image) return;

    setLoading(true);
    setError(null);

    const formData = new FormData();
    formData.append('image', image);

    try {
      let url = API_ENDPOINTS[operation];
      if (typeof url === 'function') {
        url = url(value);
      }

      const response = await fetch(url, {
        method: 'POST',
        body: formData,
        headers: {
            'Accept': 'application/json',
        },
        mode: 'cors' 
    });

      if (!response.ok) {
        throw new Error(`Processing failed: ${response.statusText}`);
      }

      const blob = await response.blob();
      const processedBlob = new File([blob], 'processed.jpg', { type: 'image/jpeg' });
      setProcessedImage(processedBlob);
      setPreview(URL.createObjectURL(blob));
      setImage(processedBlob);
    } catch (err) {
      console.error('Error:', err);
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleDownload = () => {
    if (processedImage) {
      const downloadUrl = URL.createObjectURL(processedImage);
      const a = document.createElement('a');
      a.href = downloadUrl;
      a.download = 'edited_image.jpg';
      document.body.appendChild(a);
      a.click();
      document.body.removeChild(a);
      URL.revokeObjectURL(downloadUrl);
    }
  };

  const handleBrightnessAdjust = () => {
    setModalConfig({
      title: 'Adjust Brightness',
      min: -100,
      max: 100,
      onSubmit: (value) => handleProcess('BRIGHTNESS', value),
    });
    setIsModalOpen(true);
  };

  const handleBlur = () => {
    setModalConfig({
      title: 'Apply Blur',
      min: 2,
      max: 10,
      onSubmit: (value) => handleProcess('BLUR', value),
    });
    setIsModalOpen(true);
  };

  return (
    <div className="min-h-screen bg-gray-50 py-8 px-4 sm:px-6 lg:px-8">
      <div className="max-w-4xl mx-auto">
        <div className="bg-white rounded-lg shadow-lg p-6">
          {/* Header */}
          <div className="flex flex-col sm:flex-row justify-between items-center mb-6">
            <h1 className="text-2xl font-bold text-gray-900 mb-4 sm:mb-0">Image Editor</h1>
            {processedImage && (
              <button
                onClick={handleDownload}
                className="flex items-center gap-2 px-4 py-2 bg-green-500 text-white rounded-lg hover:bg-green-600 transition-colors"
              >
                <Download size={20} />
                <span>Download</span>
              </button>
            )}
          </div>

          {/* File Upload */}
          <div className="mb-6">
            <div className="flex justify-center w-full">
              <label className="w-full flex flex-col items-center px-4 py-6 bg-white rounded-lg shadow-lg tracking-wide uppercase border border-blue-500 cursor-pointer hover:bg-blue-500 hover:text-white transition-all duration-300">
                <svg className="w-8 h-8" fill="currentColor" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20">
                  <path d="M16.88 9.1A4 4 0 0 1 16 17H5a5 5 0 0 1-1-9.9V7a3 3 0 0 1 4.52-2.59A4.98 4.98 0 0 1 17 8c0 .38-.04.74-.12 1.1zM11 11h3l-4-4-4 4h3v3h2v-3z" />
                </svg>
                <span className="mt-2 text-base leading-normal">Select an image</span>
                <input
                  type='file'
                  className="hidden"
                  accept="image/*"
                  onChange={handleImageUpload}
                />
              </label>
            </div>
          </div>

          {/* Image Preview */}
          {preview && (
            <div className="mb-6">
              <div className="relative rounded-lg overflow-hidden shadow-xl">
                <img 
                  src={preview} 
                  alt="Preview" 
                  className="w-full h-auto"
                  style={{ maxHeight: '500px', objectFit: 'contain' }}
                />
              </div>
            </div>
          )}

          {/* Edit Controls */}
          <div className="grid grid-cols-2 sm:grid-cols-3 md:grid-cols-4 gap-3">
            <button
              onClick={() => handleProcess('GRAYSCALE')}
              disabled={!image || loading}
              className="p-3 bg-blue-500 text-white rounded-lg hover:bg-blue-600 disabled:bg-gray-300 transition-colors"
            >
              Grayscale
            </button>
            <button
              onClick={() => handleProcess('ROTATE_CLOCKWISE')}
              disabled={!image || loading}
              className="p-3 bg-blue-500 text-white rounded-lg hover:bg-blue-600 disabled:bg-gray-300 transition-colors"
            >
              Rotate Clockwise
            </button>
            <button
              onClick={() => handleProcess('ROTATE_COUNTER')}
              disabled={!image || loading}
              className="p-3 bg-blue-500 text-white rounded-lg hover:bg-blue-600 disabled:bg-gray-300 transition-colors"
            >
              Rotate Counter
            </button>
            <button
              onClick={() => handleProcess('FLIP_HORIZONTAL')}
              disabled={!image || loading}
              className="p-3 bg-blue-500 text-white rounded-lg hover:bg-blue-600 disabled:bg-gray-300 transition-colors"
            >
              Flip Horizontal
            </button>
            <button
              onClick={() => handleProcess('FLIP_VERTICAL')}
              disabled={!image || loading}
              className="p-3 bg-blue-500 text-white rounded-lg hover:bg-blue-600 disabled:bg-gray-300 transition-colors"
            >
              Flip Vertical
            </button>
            <button
              onClick={handleBrightnessAdjust}
              disabled={!image || loading}
              className="p-3 bg-blue-500 text-white rounded-lg hover:bg-blue-600 disabled:bg-gray-300 transition-colors"
            >
              Brightness
            </button>
            <button
              onClick={handleBlur}
              disabled={!image || loading}
              className="p-3 bg-blue-500 text-white rounded-lg hover:bg-blue-600 disabled:bg-gray-300 transition-colors"
            >
              Blur
            </button>
          </div>

          {/* Loading State */}
          {loading && (
            <div className="mt-6 p-4 bg-blue-50 text-blue-700 rounded-lg">
              <div className="flex items-center justify-center">
                <svg className="animate-spin h-5 w-5 mr-3" viewBox="0 0 24 24">
                  <circle 
                    className="opacity-25" 
                    cx="12" 
                    cy="12" 
                    r="10" 
                    stroke="currentColor" 
                    strokeWidth="4"
                  />
                  <path 
                    className="opacity-75" 
                    fill="currentColor" 
                    d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"
                  />
                </svg>
                Processing image...
              </div>
            </div>
          )}

          {/* Error State */}
          {error && (
            <Alert variant="destructive" className="mt-6">
              <AlertDescription>{error}</AlertDescription>
            </Alert>
          )}
        </div>
      </div>

      {/* Modal */}
      <InputModal
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        {...modalConfig}
      />
    </div>
  );
};

export default ImageEditor;