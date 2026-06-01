import React, { useState } from 'react';
import api from '../../services/api'; 
import './Register.css';

const Register = () => {
  const [formData, setFormData] = useState({
    name: '',
    surname: '',
    email: '',
    cpf: '',
    phone: '',
    birthDate: '',
    password: '',
    confirmPassword: '' 
  });

  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);

  const maskCPF = (value) => {
    return value
      .replace(/\D/g, '')
      .replace(/(\d{3})(\d)/, '$1.$2')
      .replace(/(\d{3})(\d)/, '$1.$2')
      .replace(/(\d{3})(\d{1,2})$/, '$1-$2')
      .substring(0, 14);
  };

  const maskPhone = (value) => {
    return value
      .replace(/\D/g, '')
      .replace(/^(\d{2})(\d)/g, '($1) $2')
      .replace(/(\d{5})(\d)/, '$1-$2')
      .substring(0, 15);
  };

  const maskBirthDate = (value) => {
    return value
      .replace(/\D/g, '')
      .replace(/(\d{2})(\d)/, '$1/$2')
      .replace(/(\d{2})(\d)/, '$1/$2')
      .substring(0, 10);
  };

  const getPasswordStrengthScore = (password) => {
    if (!password) return 0;
    if (password.length < 8) return 1;

    let criteriaMet = 0;
    if (/[A-Z]/.test(password)) criteriaMet++;
    if (/[0-9]/.test(password)) criteriaMet++;
    if(/[^A-Za-z0-9]/.test(password)) criteriaMet++;

    if (criteriaMet <= 1) return 1;
    if (criteriaMet === 2) return 2;
    return 3;
  };

  const getPasswordFeedbackMessage = (password, score) => {
    if (!password) return '';
    if (password.length < 8) return 'Senha deve ter no menos 8 caracteres';
    if (score === 1) return 'Senha fraca';
    if (score === 2) return 'Senha moderada';
    return 'Senha forte';
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    let formattedValue = value;

    if (name === 'cpf') formattedValue = maskCPF(value);
    if (name === 'phone') formattedValue = maskPhone(value);
    if (name === 'birthDate') formattedValue = maskBirthDate(value);

    setFormData({
      ...formData,
      [name]: formattedValue
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccess('');

    if (formData.password.length < 8) {
      setError('A senha precisa ter no mínimo 8 caracteres.');
      return;
    }

    if (formData.password !== formData.confirmPassword) {
      setError('As senhas não coincidem.');
      return;
    }

    const payload = {
      name: formData.name,
      surname: formData.surname,
      email: formData.email,
      cpf: formData.cpf,
      phone: formData.phone,
      birthDate: formData.birthDate, 
      password: formData.password
    };

    try {
      await api.post('/v1/users', payload);
      setSuccess('Usuário cadastrado com sucesso!');
      setFormData({
        name: '',
        surname: '',
        email: '',
        cpf: '',
        phone: '',
        birthDate: '',
        password: '',
        confirmPassword: ''
      });
    } catch (err) {
      if (err.response && err.response.data) {
        const backendMessage = err.response.data.message;
        
        if (backendMessage === "Email is already registered.") {
          setError("Este e-mail já está cadastrado.");
        } else {
          setError(backendMessage || 'Erro ao realizar o cadastro.');
        }

      } else {
        setError('Erro de conexão com o servidor.');
      }
    }
  };

  const score = getPasswordStrengthScore(formData.password);
  const feedbackMessage = getPasswordFeedbackMessage(formData.password, score);

  return (
    <div className="register-container">
      <h2>Cadastro de Usuário</h2>
      
      {error && <div className="message error">{error}</div>}
      {success && <div className="message success">{success}</div>}

      <form onSubmit={handleSubmit}>
        <div className="form-row">
          <div className="input-group">
            <label>Nome</label>
            <input type="text" name="name" value={formData.name} onChange={handleChange} required minLength={3} />
          </div>
          <div className="input-group">
            <label>Sobrenome</label>
            <input type="text" name="surname" value={formData.surname} onChange={handleChange} required minLength={3} />
          </div>
          <div className="input-group">
            <label>E-mail</label>
            <input type="email" name="email" value={formData.email} onChange={handleChange} required />
          </div>
          <div className="input-group">
            <label>CPF</label>
            <input type="text" name="cpf" placeholder="000.000.000-00" value={formData.cpf} onChange={handleChange} required />
          </div>
        </div>

        <div className="form-row alignment-fix">
          <div className="input-group">
            <label>Telefone</label>
            <input type="text" name="phone" placeholder="(11) 99999-9999" value={formData.phone} onChange={handleChange} required />
          </div>
          <div className="input-group">
            <label>Data de Nascimento</label>
            <input type="text" name="birthDate" placeholder="DD/MM/AAAA" value={formData.birthDate} onChange={handleChange} required />
          </div>
          
          <div className="input-group">
            <label>Senha</label>
            <div className="password-input-wrapper">
              <input type={showPassword ? "text" : "password"} name="password" value={formData.password} onChange={handleChange} required />
              <button type="button" className="password-toggle-btn" onClick={() => setShowPassword(!showPassword)}>
                {showPassword ? (
                  <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M2 12s3-7 10-7 10 7 10 7-3 7-10 7-10-7-10-7Z"/><circle cx="12" cy="12" r="3"/></svg>
                ) : (
                  <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M9.88 9.88a3 3 0 1 0 4.24 4.24"/><path d="M10.73 5.08A10.43 10.43 0 0 1 12 5c7 0 10 7 10 7a13.16 13.16 0 0 1-1.67 2.68"/><path d="M6.61 6.61A13.52 13.52 0 0 0 2 12s3 7 10 7a9.74 9.74 0 0 0 5.39-1.61"/><line x1="2" y1="2" x2="22" y2="22"/></svg>
                )}
              </button>
            </div>

            {formData.password && (
              <div className="password-strength-container">
                <div className="strength-bars-row">
                  <div className={`bar ${score >= 1 ? (formData.password.length < 8 ? 'weak-red' : score === 1 ? 'red' : score === 2 ? 'orange' : 'green') : ''}`} />
                  <div className={`bar ${score >= 2 ? (score === 2 ? 'orange' : 'green') : ''}`} />
                  <div className={`bar ${score === 3 ? 'green' : ''}`} />
                </div>
                <span className={`strength-text-feedback ${formData.password.length < 8 || score === 1 ? 'text-red' : score === 2 ? 'text-orange' : 'text-green'}`}>
                  {feedbackMessage}
                </span>
              </div>
            )}
          </div>

          <div className="input-group">
            <label>Confirmar senha</label>
            <div className="password-input-wrapper">
              <input type={showConfirmPassword ? "text" : "password"} name="confirmPassword" value={formData.confirmPassword} onChange={handleChange} required />
              <button type="button" className="password-toggle-btn" onClick={() => setShowConfirmPassword(!showConfirmPassword)}>
                {showConfirmPassword ? (
                  <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M2 12s3-7 10-7 10 7 10 7-3 7-10 7-10-7-10-7Z"/><circle cx="12" cy="12" r="3"/></svg>
                ) : (
                  <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M9.88 9.88a3 3 0 1 0 4.24 4.24"/><path d="M10.73 5.08A10.43 10.43 0 0 1 12 5c7 0 10 7 10 7a13.16 13.16 0 0 1-1.67 2.68"/><path d="M6.61 6.61A13.52 13.52 0 0 0 2 12s3 7 10 7a9.74 9.74 0 0 0 5.39-1.61"/><line x1="2" y1="2" x2="22" y2="22"/></svg>
                )}
              </button>
            </div>
          </div>
        </div>

        <button type="submit" className="btn-submit">Cadastrar</button>

        <div className="login-redirect-container">
          <span>Já tem uma conta ?</span>
          <a href="/login" className="btn-login-redirect">ENTRAR</a>
        </div>
        
      </form>
    </div>
  );
};

export default Register;