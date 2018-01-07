'use strict'

const express = require('express')
const userCtrl = require('../controllers/user')
const foodCtrl = require('../controllers/food')
const chatCtrl = require('../controllers/chat')

const api = express.Router()

//User routes
api.post('/user', userCtrl.createUser)
api.put('/user/:userId', userCtrl.updateUser)
api.put('/user/token/:userId', userCtrl.updateTokenId)
api.delete('/user/:userId', userCtrl.deleteUser)
api.get('/user/:userId', userCtrl.getUser)
api.get('/user/login/:email/:password', userCtrl.loginUser)


//Food routes
api.post('/food', foodCtrl.createFood)
api.put('/food/:foodId', foodCtrl.updateFood)
api.delete('/food/:foodId', foodCtrl.deleteFood)
api.get('/food/:foodId', foodCtrl.getFood)
api.get('/foodlist/:page', foodCtrl.getFoodListByLocation)
//TODO api.get('/food/location/:lat/:long', foodCtrl.getFoodListByLocation)


//Chat routes
api.post('/chat/', chatCtrl.createChat)
api.delete('/chat/:chatId', chatCtrl.deleteChat)
api.get('/chat/:chatId', chatCtrl.getChat)
api.put('/chat/:chatId', chatCtrl.newChatMessage)


module.exports = api
