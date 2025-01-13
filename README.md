# Image Editor Web Application

A full-stack web application for real-time image editing, built with Next.js frontend and Spring Boot backend. This application provides various image manipulation features with an intuitive user interface.


## Features

- Basic Image Operations:
  - Grayscale conversion
  - Clockwise and counter-clockwise rotation
  - Horizontal and vertical flipping
- Advanced Image Adjustments:
  - Brightness control (-100 to +100)
  - Blur effect (radius 2-10)
- Real-time preview
- Download processed images
- Responsive design
- Error handling and loading states

## Tech Stack

### Frontend
- Next.js
- React
- Tailwind CSS
- Shadcn/UI components
- Lucide React icons

### Backend
- Spring Boot
- Java Image I/O
- Spring Web
- Spring MVC

## Prerequisites

- Node.js (v14 or higher)
- Java JDK 11 or higher
- Maven
- npm or yarn

## Installation

### Backend Setup

1. Clone the repository:
```bash
git clone git@github.com:sgnhyperion/ImageEditorproject.git
cd image-editor
```

2. Navigate to the backend directory and build the project:
```bash
cd backend
mvn clean install
```

3. Run the Spring Boot application:
```bash
mvn spring-boot:run
```

The backend server will start at `http://localhost:8082`

### Frontend Setup

1. Navigate to the frontend directory:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
# or
yarn install
```

3. Create a `.env.local` file:
```env
NEXT_PUBLIC_API_URL=http://localhost:8082
```

4. Run the development server:
```bash
npm run dev
# or
yarn dev
```

The frontend application will be available at `http://localhost:3000`

## API Endpoints

| Endpoint | Method | Description |
|----------|---------|-------------|
| `/api/process/grayscale` | POST | Convert image to grayscale |
| `/api/process/rotate-clockwise` | POST | Rotate image 90° clockwise |
| `/api/process/rotate-counter` | POST | Rotate image 90° counter-clockwise |
| `/api/process/flip-horizontal` | POST | Flip image horizontally |
| `/api/process/flip-vertical` | POST | Flip image vertically |
| `/api/process/brightness/{value}` | POST | Adjust image brightness |
| `/api/process/blur/{radius}` | POST | Apply Gaussian blur |

## Usage

1. Open the application in your web browser
2. Click "Select an image" to upload an image
3. Use the available editing buttons to modify your image
4. Preview changes in real-time
5. Click "Download" to save the processed image


