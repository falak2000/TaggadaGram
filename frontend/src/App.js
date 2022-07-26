
import './App.css';
import { Container } from '@material-ui/core';
import Navbar from './components/Navbar/Navbar';
import Posts from './components/Posts/Posts';

function App() {


  return (
    <Container maxWidth='md' >
        <Navbar />
        <Posts />
    </Container>
  );
}

export default App;
