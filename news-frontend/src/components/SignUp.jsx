import {useState, useRef, useEffect} from "react";
import {useNavigate} from "react-router-dom";
import womanNewspaper from '../assets/woman-reading-newspaper.mp4';

function SignUp(){

    const[firstName, setfirstName] = useState("");
    const[lastName, setLastName] = useState("");
    const[username, setUsername] = useState("");
    const[email, setEmail] = useState("");
    const[password, setPassword] = useState("");
    const[errors, setErrors] = useState({});
    const[isSignUp, setIsSignUp] = useState(true); // true for sign up, false for sign in
    const navigate = useNavigate()
    const videoRef = useRef(null);
    
    useEffect(() => {
        if (videoRef.current) {
            videoRef.current.playbackRate = 0.75; // Set to 0.75 speed (75% of normal speed)
        }
    }, []);

    const HandleSignup = async () => {
        const userData = {username: username, email: email, firstName: firstName, lastName: lastName, password: password,}

        const response = await fetch('http://localhost:8080/api/users', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(userData)
        });

        if (response.ok){
            const data = await response.json()
            localStorage.setItem('token', data.token);
            navigate('/home');
        }
        else{
            const errorData = await response.json();
            setErrors(errorData);
        }

    }

    const HandleSignin = async () => {
        const userData = {username: username, password: password}

        const response = await fetch('http://localhost:8080/api/auth/login', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
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

    return(
        <>
        <div className="relative flex h-screen w-full items-center justify-center overflow-hidden">
            <video ref={videoRef} autoPlay loop muted className="absolute z-0 min-h-full min-w-full object-cover brightness-50 blur-sm">
                <source src={womanNewspaper} type="video/mp4"/>
                Your browser does not support the video tag.
            </video>

            <div className="relative z-10 bg-gradient-to-b from-blue-200/20 to-transparent backdrop-blur-md rounded-3xl border border-white/30 shadow-lg p-8 max-w-md w-full mx-4">
                <div className="absolute top-4 left-4">
                    <button
                        type="button"
                        onClick={() => setIsSignUp(!isSignUp)}
                        className="relative inline-flex h-8 w-32 items-center rounded-full bg-white/20 backdrop-blur-sm border border-white/30 transition-colors focus:outline-none focus:ring-2 focus:ring-blue-300"
                    >
                        <span
                            className={`absolute left-3 top-1/2 transform -translate-y-1/2 text-xs font-bold z-10 text-white`}
                        >
                            Sign In
                        </span>
                        <span
                            className={`absolute right-2 top-1/2 transform -translate-y-1/2 text-xs font-bold z-10 text-white`}
                        >
                            Sign Up
                        </span>
                        <span
                            className={`inline-block h-6 w-14 transform rounded-full bg-blue-400 shadow transition-transform duration-200 z-0 ${
                                isSignUp ? 'translate-x-17' : 'translate-x-1'
                            }`}
                        />
                    </button>
                </div>

                <h2 className="text-2xl font-bold text-white text-center mb-6">{isSignUp ? 'Sign Up' : 'Sign In'}</h2>
                <form onSubmit={(e) => { e.preventDefault(); isSignUp ? HandleSignup() : HandleSignin(); }} className="space-y-4">
                    {isSignUp && (
                        <>
                            <div className="grid grid-cols-2 gap-4">
                                <div>
                                    <label className="block text-white mb-1">First name</label>
                                    <input
                                        type="text"
                                        value={firstName}
                                        className={`w-full px-4 py-2 rounded-lg bg-white/10 ${errors.firstName ? 'border border-red-400' : 'border border-white/20'} text-white placeholder-white/70 focus:outline-none focus:ring-2 focus:ring-blue-300`}
                                        onChange={(e) => setfirstName(e.target.value)}
                                        placeholder="First name"
                                    />
                                    {errors.firstName && <span className="text-red-400 text-sm mt-1 block">{errors.firstName}</span>}
                                </div>
                                <div>
                                    <label className="block text-white mb-1">Last name</label>
                                    <input
                                        type="text"
                                        value={lastName}
                                        className={`w-full px-4 py-2 rounded-lg bg-white/10 ${errors.lastName ? 'border border-red-400' : 'border border-white/20'} text-white placeholder-white/70 focus:outline-none focus:ring-2 focus:ring-blue-300`}
                                        onChange={(e) => setLastName(e.target.value)}
                                        placeholder="Last name"
                                    />
                                    {errors.lastName && <span className="text-red-400 text-sm mt-1 block">{errors.lastName}</span>}
                                </div>
                            </div>

                            <div>
                                <label className="block text-white mb-1">Email</label>
                                <input
                                    type="email"
                                    value={email}
                                    className={`w-full px-4 py-2 rounded-lg bg-white/10 ${errors.email ? 'border border-red-400' : 'border border-white/20'} text-white placeholder-white/70 focus:outline-none focus:ring-2 focus:ring-blue-300`}
                                    onChange={(e) => setEmail(e.target.value)}
                                    placeholder="Enter your email"
                                />
                                {errors.email && <span className="text-red-400 text-sm mt-1 block">{errors.email}</span>}
                            </div>
                        </>
                    )}

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
                        {isSignUp ? 'Sign Up' : 'Sign In'}
                    </button>
                </form>
            </div>
        </div>
        </>
    )
}

export default SignUp;