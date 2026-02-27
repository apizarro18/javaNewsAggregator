import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import Welcome from './components/Welcome'
import Login from './components/Login'
import './App.css'

function App() {
  //JS Section

  const [isVisible, setisVisible] = useState(true)

  //JSX Markup
  return (
    <>  

    <Login></Login>

    </>
  )
}

export default App
