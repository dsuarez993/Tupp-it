'use strict'

const express = require('express')
const bodyParser = require('body-parser')
const app = express();
const api = require('./routes')
const userCtrl = require('./controllers/user')

app.use(bodyParser.urlencoded({extended: false,limit: '6mb'}))
app.use(bodyParser.json({limit: '6mb'}))

app.use('/api',api)

module.exports = app
