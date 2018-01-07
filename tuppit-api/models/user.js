'use strict'

const mongoose = require('mongoose')
const Schema = mongoose.Schema
//const bcrypt = require('bcrypt-nodejs')

const UserSchema = new Schema({
  email: {type: String, unique: true, lowercase:true},
  displayName: String,
  password: {type: String, select: false},
  avatar: String,
  singupDate: Date,
  tokenId: String,
  description: String,
  foods:[{foodId:{type: Schema.Types.ObjectId, ref: 'Food'}}],
  chats:[{chatId:{type: Schema.Types.ObjectId, ref: 'Chat'},
          userId:{type: Schema.Types.ObjectId, ref: 'User'},
          foodId:{type: Schema.Types.ObjectId, ref: 'Food'}}]
})

module.exports= mongoose.model('User', UserSchema)
