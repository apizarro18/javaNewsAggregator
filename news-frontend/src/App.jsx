import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

function App() {
  const [count, setCount] = useState(0)


  //JS Section

  function Welcome(username){
    return <div>Welcome {username}!</div>
  }

  function SaveInput(){
    const inputField = document.getElementById("username-input")
    const enteredName = inputField.value
    setUsername(enteredName)
  }

  const [username, setUsername] = useState("")

  //JSX Markup
  return (
    <>
      <label>
        Enter your username: 
        <input type="text" id="username-input"></input>


      </label>
    </>
  )
}

export default App
