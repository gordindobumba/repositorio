const UserRepository = require("../repository/userRepository");

class LoginService {
  static async authenticate(data) {
    const { email, password } = data;

    const user = await UserRepository.findUserByEmail(email);

    if (user && user.password === password) {
      return { success: true };
    }
    return { success: false };
  }
}

module.exports = LoginService;
