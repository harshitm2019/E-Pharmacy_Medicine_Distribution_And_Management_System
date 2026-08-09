import { useState } from "react";

import Visibility from "@mui/icons-material/Visibility";
import VisibilityOff from "@mui/icons-material/VisibilityOff";
import {
    IconButton,
    InputAdornment, TextField
} from "@mui/material";

function PasswordField({
    label,
    error,
    helperText,
    register
}) {
    const [showPassword, setShowPassword] = useState(false);

    function togglePassword() {

        setShowPassword(previous => !previous);

    }

    return (

        <TextField
            fullWidth
            label={label}
            type={showPassword ? "text" : "password"}
            error={error}
            helperText={helperText}
            {...register}
            slotProps={{
                input: {
                    endAdornment: (
                        <InputAdornment position="end">
                            <IconButton
                                edge="end"
                                onClick={togglePassword}
                                onMouseDown={(e) => e.preventDefault()}
                            >
                                {showPassword ? <Visibility /> : <VisibilityOff />}
                            </IconButton>
                        </InputAdornment>
                    )
                }
            }}
        />

    );
}
export default PasswordField;