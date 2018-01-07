module.exports = {
  port: process.env.PORT || 3000,
  db: process.env.MONGO || 'mongodb://localhost:27017/yummit',
  ssl_certificate:'sslcert/server.crt',
  ssl_privatekey:'sslcert/key.pem'
}
