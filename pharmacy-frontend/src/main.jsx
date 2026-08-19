import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { Toaster } from "react-hot-toast";
import App from './App.jsx';
import "./styles/responsive.css";

import CssBaseline from "@mui/material/CssBaseline";
import { ThemeProvider } from "@mui/material/styles";
import { AuthProvider } from "./context/AuthContext";
import theme from "./theme/theme";

const queryClient = new QueryClient();

createRoot(document.getElementById('root')).render(
  <StrictMode>

    <ThemeProvider theme={theme}>

      <CssBaseline />

      <Toaster position="top-right" />

      <AuthProvider>

        <QueryClientProvider client={queryClient}>

          <App />

        </QueryClientProvider>

      </AuthProvider>

    </ThemeProvider>

  </StrictMode>,
)
