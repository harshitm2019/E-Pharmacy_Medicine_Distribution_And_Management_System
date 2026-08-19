import { z } from "zod";

export const createUserSchema = z.object({
    username: z.string().trim().min(3, "Username must be between 3 and 100 characters.").
    max(100, "Username must be between 3 and 100 characters."),
    email: z.string().trim().email("Enter a valid email."),
    password: z.string().min(8, "Password must be between 8 and 20 characters.")
    .max(20, "Password must be between 8 and 20 characters."),
    phoneNumber: z.string().regex(/^[6-9]\d{9}$/, "Enter a valid phone number."),
    address: z.string().trim().min(1, "Address is required."),
    city: z.string().trim().min(1, "City is required."),
    state: z.string().trim().min(1, "State is required."),
    pin: z.string().regex(/^\d{6}$/, "Enter a valid PIN."),
    role: z.enum(["ADMIN", "DELIVERY_BOY"], { message: "Role is required." })
});