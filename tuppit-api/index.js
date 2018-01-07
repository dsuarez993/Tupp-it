'use strict'

const mongoose = require('mongoose')
const app = require('./app')
const config = require('./config')

const fs = require('fs')
const http = require('http')
const https = require('https')
const privateKey  = fs.readFileSync(config.ssl_privatekey, 'utf8')
const certificate = fs.readFileSync(config.ssl_certificate, 'utf8')
const credentials = {key: privateKey, cert: certificate}

mongoose.connect(config.db, (err, res) => {
  if(err){
    return console.log(`Error al conectar a la base de datos: ${err}`)
  }
  console.log('Conexion a la base de datos establecida')

  const httpServer = http.createServer(app);
  const httpsServer = https.createServer(credentials, app);

  /*httpServer.listen(config.port, ()=>{
    console.log(`API REST corriendo en https://localhost:${config.port}`)
  })*/

  httpsServer.listen(config.port, ()=>{
    console.log(`API REST corriendo en https://localhost:${config.port}`)
  })


})
