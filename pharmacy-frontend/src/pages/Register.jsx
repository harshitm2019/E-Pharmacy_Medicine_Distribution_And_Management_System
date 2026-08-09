import AuthLayout from "../components/auth/AuthLayout";
import RegisterForm from "../components/auth/RegisterForm";

function Register() {

    return (
        <AuthLayout
            title="Create Account"
            subtitle="Create your customer account."
        >
            <RegisterForm />
        </AuthLayout>
    );

}

export default Register;