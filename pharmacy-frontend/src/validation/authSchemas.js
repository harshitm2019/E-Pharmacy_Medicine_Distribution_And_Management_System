import { z } from "zod";


export const loginSchema = z.object({

    email: z
        .email("Invalid email")
        .trim(),

    password: z
        .string()
        .min(1, "Password is required.")

});

export const registerSchema = z.object({

    username: z
        .string()
        .trim()
        .min(3, "Username must be at least 3 characters.")
        .max(100, "Username must not exceed 100 characters."),

    email: z
        .email("Invalid email")
        .trim(),

    phone: z
        .string()
        .regex(
            /^[6-9]\d{9}$/,
            "Phone number must be Valid Indian Mobile Number."
        ),

    password: z
        .string()
        .min(8, "Password must be at least 8 characters.")
        .max(20, "Password must not exceed 20 characters."),

    address: z
        .string()
        .trim()
        .min(1, "Address is required."),

    city: z
        .string()
        .trim()
        .min(1, "City is required."),

    state: z
        .string()
        .trim()
        .min(1, "State is required."),

    pin: z
        .string()
        .regex(
            /^\d{6}$/,
            "Pin code must be exactly 6 digits."
        )

});