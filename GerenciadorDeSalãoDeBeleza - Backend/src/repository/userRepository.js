const MOCK_DB = [
  { id: 1, email: 'ju@gmail.com', password: '12345' }
];

class UserRepository {

  static async findUserByEmail(email) {
    const user = MOCK_DB.find(u => u.email === email);
    return user;
  }
}

module.exports = UserRepository;
