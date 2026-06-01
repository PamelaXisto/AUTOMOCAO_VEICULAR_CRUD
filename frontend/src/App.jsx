import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Register from './pages/Register/Register'; // Certifique-se de que o caminho do seu Register está correto

function App() {
  return (
    <Router>
      <Routes>
        {/* Rota Principal (Home) - Por enquanto exibe o título */}
        <Route 
          path="/" 
          element={
            <div>
              <h1>PX AutoSolution em React!</h1>
              <p>Bem-vido à Home. Vá para <a href="/register">/register</a> para se cadastrar.</p>
            </div>
          } 
        />
        
        {/* Rota para a nossa tela de cadastro de usuário */}
        <Route path="/register" element={<Register />} />
      </Routes>
    </Router>
  );
}

export default App;