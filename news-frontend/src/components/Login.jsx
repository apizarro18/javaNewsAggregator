import { useState } from "react";

function Login(){
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [email, setEmail] = useState("");
    const [user, setUser] = useState(null);
    const [isVisible, setVisibility] = useState(true)
    const [errors, setErrors] = useState({});



    const HandleSignup = async () => {
        const userData = {username: username, email: email, password: password,}

        const response = await fetch('http://localhost:8080/api/users', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(userData)
        });

        if (response.ok){
            setVisibility(false)
        }
        else{
            const errorData = await response.json();
            setErrors(errorData);
        }

    }

    function SuccessSignup({username}){
        return <div> Thanks for signing up, {username}!</div>
    }

    return (
        <>

        <div className="input-group">
            <>
            {isVisible&&(
            <div className="input-fields">
                <label>
                    Enter your email:
                    <input 
                        type="text" 
                        value={email} 
                        className={errors.email ? "error-border": ""}
                        onChange={(e) => setEmail(e.target.value)}>
                    </input>
                    {errors.email && <span className="error-msg">{errors.email}</span>}
                </label>

                <label>
                    Enter your username:
                    <input 
                        type="text" 
                        value={username} 
                        className={errors.username ? "error-border": ""}
                        onChange={(e) => setUsername(e.target.value)}>
                    </input>
                    {errors.username && <span className="error-msg">{errors.username}</span>}
                </label>

                <label>
                    Enter your password:
                    <input 
                        type="text" 
                        value={password} 
                        className={errors.password ? "error-border": ""}
                        onChange={(e) => setPassword(e.target.value)}>
                    </input>
                    {errors.password && <span className="error-msg">{errors.password}</span>}
                </label>
            </div>
            )}
            </>
        </div>

        <div className="button&message">
            {isVisible&& (
            <button type="submit" className="bg-blue-500 text-white font-bold p-4 rounded-full" onClick={HandleSignup}>
                Sign Up!
            </button>
            )}

            {!isVisible&&(
                <SuccessSignup username={username}/>
            )}
        </div>
        </>
    )

}

export default Login;