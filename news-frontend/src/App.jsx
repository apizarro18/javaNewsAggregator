import { useState } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './index.css';
import SignUp from './components/SignUp';
import Login from './components/Login';
import Welcome from './components/Welcome';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/Home" element={<Home />}/>
        <Route path="/SignUp" element={<Login/>}/>
        <Route path="/Login" element={<Login/>}/>
      </Routes>
    </Router>
  )
}

export default App
