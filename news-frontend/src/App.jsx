import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

function App() {
  //JS Section

  function Welcome(props){

    if (loggedIn == true){
      return <div>Welcome {props.username}!</div>
    }
    else{
      return <div>Please log in!</div>
    }
  }

  const [loggedIn, setStatus] = useState(false)
  const [username, setUsername] = useState("")

  //JSX Markup
  return (
    <>
      <label>
        Enter your username: 
        <input type="text" value={username} onChange={(e) => setUsername(e.target.value)}></input>
      </label>
      <button onClick={() => setStatus(true)}>Save</button>
      <Welcome username={username}/>
    </>
  )
}

export default App
