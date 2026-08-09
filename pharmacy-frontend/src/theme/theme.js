import { createTheme } from "@mui/material/styles";

const theme = createTheme({

    palette: {

        primary: {
            main: "#1976d2",
        },

        secondary: {
            main: "#2e7d32",
        },

        error: {
            main: "#d32f2f",
        },

        warning: {
            main: "#ed6c02",
        },

        background: {
            default: "#f5f7fa",
            paper: "#ffffff",
        }

    },

    typography: {

        fontFamily: "Roboto, Arial, sans-serif",

        h4: {
            fontWeight: 700,
        },

        h5: {
            fontWeight: 600,
        },

        button: {
            textTransform: "none",
            fontWeight: 600
        }

    },

    shape: {
        borderRadius: 10
    }

});

export default theme;