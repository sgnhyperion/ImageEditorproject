const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8082';

const API_ENDPOINTS = {
    GRAYSCALE: `${API_BASE_URL}/api/process/grayscale`,
    ROTATE_CLOCKWISE: `${API_BASE_URL}/api/process/rotate-clockwise`,
    ROTATE_COUNTER: `${API_BASE_URL}/api/process/rotate-counter`,
    FLIP_HORIZONTAL: `${API_BASE_URL}/api/process/flip-horizontal`,
    FLIP_VERTICAL: `${API_BASE_URL}/api/process/flip-vertical`,
    BRIGHTNESS: (value) => `${API_BASE_URL}/api/process/brightness/${value}`,
    BLUR: (radius) => `${API_BASE_URL}/api/process/blur/${radius}`
};

export default API_ENDPOINTS;