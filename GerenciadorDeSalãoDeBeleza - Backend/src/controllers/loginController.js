const LoginService = require('../services/loginService');

class LoginController {
  static async login(req, res) {
    const { email, password } = req.body;

    const result = await LoginService.authenticate({ email, password });

    if (result.success) {
      return res.status(200).json({
        message: 'Login realizado com sucesso',
      });
    } else {
      return res.status(401).json({
        message: 'Credenciais inválidas',
      });
    }
  }
}

module.exports = LoginController;
