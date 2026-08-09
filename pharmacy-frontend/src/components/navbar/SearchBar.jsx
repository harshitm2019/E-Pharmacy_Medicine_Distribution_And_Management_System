import SearchIcon from "@mui/icons-material/Search";
import { InputAdornment, TextField } from "@mui/material";

function SearchBar() {

    return (

        <TextField fullWidthsize="medium"
            placeholder="Search medicines, wellness products..."
            variant="outlined"
            slotProps={{
                input: {
                    startAdornment: (
                        <InputAdornment position="start">
                            <SearchIcon color="action" />
                        </InputAdornment>
                    )
                }
            }}
            sx={{
                width: "100%",

                "& .MuiOutlinedInput-root": {
                    height: 52,
                    borderRadius: "50px",
                    backgroundColor: "#ffffff",
                    transition: "all .3s ease",

                    "& fieldset": {
                        borderColor: "#dfe3e8"
                    },

                    "&:hover fieldset": {
                        borderColor: "#2E7D32"
                    },

                    "&.Mui-focused fieldset": {
                        borderWidth: 2,
                        borderColor: "#2E7D32"
                    },

                    "&:hover": {
                        boxShadow: "0 6px 18px rgba(0,0,0,.08)"
                    }
                }
            }}
        />
    );
}
export default SearchBar;