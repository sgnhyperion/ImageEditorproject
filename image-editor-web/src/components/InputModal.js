import React, { useState } from 'react';

const InputModal = ({ isOpen, onClose, onSubmit, title, min, max, defaultValue = 0 }) => {
  const [value, setValue] = useState(defaultValue);
  const [error, setError] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    const numValue = parseInt(value);
    if (numValue >= min && numValue <= max) {
      onSubmit(numValue);
      onClose();
    } else {
      setError(`Please enter a value between ${min} and ${max}`);
    }
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div className="bg-white rounded-lg p-6 w-96 max-w-md">
        <h2 className="text-xl font-semibold mb-4">{title}</h2>
        <form onSubmit={handleSubmit}>
          <div className="mb-4">
            <input
              type="number"
              value={value}
              onChange={(e) => {
                setValue(e.target.value);
                setError('');
              }}
              className="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
              min={min}
              max={max}
            />
            {error && <p className="text-red-500 text-sm mt-1">{error}</p>}
          </div>
          <div className="flex justify-end gap-2">
            <button
              type="button"
              onClick={onClose}
              className="px-4 py-2 text-gray-600 hover:bg-gray-100 rounded"
            >
              Cancel
            </button>
            <button
              type="submit"
              className="px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
            >
              Apply
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default InputModal;