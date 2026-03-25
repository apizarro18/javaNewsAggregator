import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import './index.css';
import SignUp from './components/SignUp';
import Login from './components/Login';
import Welcome from './components/Welcome';
import Home from './components/Home';
import TopicPicker from "./components/TopicPicker";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Welcome />}/>
        <Route path="/home" element={<Home />}/>
        <Route path="/signup" element={<SignUp />}/>
        <Route path="/login" element={<Login />}/>
        <Route path="/topicpicker" element={<TopicPicker/>}/>
      </Routes>
    </Router>
  )
}

export default App
