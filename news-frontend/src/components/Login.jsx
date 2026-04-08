import { useState } from "react";
import { useNavigate } from "react-router-dom";

function Login(){
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [errors, setErrors] = useState({});
    const navigate = useNavigate();

    const HandleSignin = async () => {
        const userData = { username: username, password: password }

        const response = await fetch('http://localhost:8080/api/auth/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(userData)
        });

        if (response.ok){
            const data = await response.json();
            localStorage.setItem('token', data.token);
            navigate('/home');
        }
        else{
            const errorData = await response.json();
            setErrors(errorData);
        }
    }

    return (
        <div className="flex h-screen w-full items-center justify-center bg-gray-900">
            <div className="bg-white/10 backdrop-blur-md rounded-3xl border border-white/30 shadow-lg p-8 max-w-md w-full mx-4">
                <h2 className="text-2xl font-bold text-white text-center mb-6">Sign In</h2>
                <form onSubmit={(e) => { e.preventDefault(); HandleSignin(); }} className="space-y-4">
                    <div>
                        <label className="block text-white mb-1">Username</label>
                        <input
                            type="text"
                            value={username}
                            className={`w-full px-4 py-2 rounded-lg bg-white/10 ${errors.username ? 'border border-red-400' : 'border border-white/20'} text-white placeholder-white/70 focus:outline-none focus:ring-2 focus:ring-blue-300`}
                            onChange={(e) => setUsername(e.target.value)}
                            placeholder="Enter your username"
                        />
                        {errors.username && <span className="text-red-400 text-sm mt-1 block">{errors.username}</span>}
                    </div>

                    <div>
                        <label className="block text-white mb-1">Password</label>
                        <input
                            type="password"
                            value={password}
                            className={`w-full px-4 py-2 rounded-lg bg-white/10 ${errors.password ? 'border border-red-400' : 'border border-white/20'} text-white placeholder-white/70 focus:outline-none focus:ring-2 focus:ring-blue-300`}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="Enter your password"
                        />
                        {errors.password && <span className="text-red-400 text-sm mt-1 block">{errors.password}</span>}
                    </div>

                    <button
                        type="submit"
                        className="w-full bg-blue-400 hover:bg-blue-500 text-white font-bold py-2 px-4 rounded-lg transition duration-200"
                    >
                        Sign In
                    </button>
                </form>
            </div>
        </div>
    )
}

export default Login;
