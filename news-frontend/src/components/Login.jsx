import { useState } from "react";

function Login(){
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [user, setUser] = useState(null);
    const [isVisible, setVisibility] = useState(true)



    const HandleSignup = async () => {
        const userData = {username: username, password: password}

        const response = await fetch('http://localhost:8080/api/users', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(userData)
        });

        if (response.ok){
            setVisibility(false)
        }

    }

    function SuccessSignup(props){
        if(isVisible == false){
            return <div> Welcome, {props.username}! </div>   
        }
    }

    return (
        <>
        
        <div>
        {isVisible&& (
        <label>
        Enter your username: 
        <input type="text" value={username} onChange={(e) => setUsername(e.target.value)}></input>
        </label>
        )}
        </div>

        <div>
        {isVisible&& (

        <label>
        Enter your password:
        <input type="text" value={password} onChange={(e) => setPassword(e.target.value)}></input>
        </label>          
        )}
        </div>

        <div>
        {isVisible&& (
        <button onClick={HandleSignup}>Create Account</button>
        )}
        <SuccessSignup isVisible={isVisible} username={username}></SuccessSignup>
        </div>
        </>
    )

}

export default Login;