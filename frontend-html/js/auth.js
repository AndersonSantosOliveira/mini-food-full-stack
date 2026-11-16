document.addEventListener('DOMContentLoaded', () => {
    const formLogin = document.querySelector('#form-login') || document.querySelector('form');

    if (formLogin) {
        formLogin.addEventListener('submit', async (e) => {
            e.preventDefault();

            // Pega os campos de e-mail e senha do formulário
            const emailInput = document.querySelector('#email') || document.querySelector('input[type="email"]');
            const senhaInput = document.querySelector('#senha') || document.querySelector('input[type="password"]');

            if (!emailInput || !senhaInput) {
                console.error('Campos de login não encontrados no HTML.');
                return;
            }

            const email = emailInput.value;
            const senha = senhaInput.value;

            try {
                const response = await fetch('http://localhost:8080/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ email, senha })
                });

                if (response.ok) {
                    const data = await response.json();
                    
                    // Salva o token JWT no localStorage
                    if (data.token) {
                        localStorage.setItem('token', data.token);
                    } else if (typeof data === 'string') {
                        localStorage.setItem('token', data);
                    }

                    // Redireciona imediatamente para o cardápio ou página principal sem travar em alertas
                    window.location.href = 'cardapio.html';
                } else {
                    alert('E-mail ou senha inválidos. Verifique seus dados.');
                }
            } catch (error) {
                console.error('Erro na requisição de login:', error);
                alert('Não foi possível conectar ao servidor. Verifique se o Back-end está rodando na porta 8080.');
            }
        });
    }
});