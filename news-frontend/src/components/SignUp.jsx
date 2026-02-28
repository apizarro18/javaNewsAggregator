import {useState} from "react";
import {useNavigate} from "react-router-dom";
import womanNewspaper from './assets/woman-reading-newspaper.mp4';

function SignUp(){

    const[firstName, setfirstName] = useState("");
    const[lastName, setLastName] = useState("");
    const[email, setEmail] = useState("");
    const[password, setPassword] = useState("");
    const[errors, setErrors] = useState({});
    const navigate = useNavigate()


    const HandleSignup = async () => {
        const userData = {username: username, email: email, firstName: firstName, lastName: lastName, password: password,}

        const response = await fetch('http://localhost:8080/api/users', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(userData)
        });

        if (response.ok){
            navigate('/home');
        }
        else{
            const errorData = await response.json();
            setErrors(errorData);
        }

    }

    return(
        <>
        <div>
            <video autoplay loop muted class="h-screen">
                <source src={womanNewspaper} type="video/mp4"/>
                Your browser does not support the video tag.
            </video>
        </div>

            <label>
                First name 
                <input
                    type="text"
                    value={firstName}
                    className={errors.firstName ? "error-border": ""}
                    onChange={(e) => setfirstName(e.target.value)}>
                </input>
                {errors.lastName && <span className="error-msg">{errors.firstName}</span>}
            </label>

            <label>
                Last name 
                <input
                    type="text"
                    value={lastName}
                    className={errors.lastName ? "error-border": ""}
                    onChange={(e) => setLastName(e.target.value)}>
                </input>
                {errors.lastName && <span className="error-msg">{errors.lastName}</span>}
            </label>

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

            <button 
                type="submit" onClick={HandleSignup}>
            </button>

        <div>

        </div>
        </>
    )
}

export default SignUp;