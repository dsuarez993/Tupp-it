'use strict'

const mongoose = require('mongoose')
const Chat = require('../models/chat')
const User = require('../models/user')
const notifications = require('../controllers/notifications')


function createChat(req, res){
  console.log('createChat')

  console.log(req.body)

  let foodId = req.body.foodId
  let uid1 = req.body.uid1
  let uid2 = req.body.uid2


  const chat = new Chat({
    foodId: foodId,
    users: [
      {userId:uid1},
      {userId:uid2}
    ]
  })

  console.log("Llega")

  User.findById(uid1, (err,user)=>{
    if(err) return res.status(500).send({message:`Error al crear chat: ${err}`})
    if(!user) return res.status(500).send({message:`Error al crear chat: ${err}`})
  })

  User.findById(uid2, (err,user)=>{
    if(err) return res.status(500).send({message:`Error al crear chat: ${err}`})
    if(!user) return res.status(500).send({message:`Error al crear chat: ${err}`})
  })

  chat.save((err, chatStored)=>{
    if(err) res.status(500).send({message:`Error al crear el chat en la base de datos: ${err}`})

    const chatId=chatStored._id

    let chat1 = {
            chatId:chatId,
            userId:uid2,
            foodId: foodId
          }

    User.findByIdAndUpdate(uid1,
      {$push: {"chats": chat1}},
      {safe: true, upsert: true, new : true},(err, userUpdated)=>{
      if(err){
        chatStored.remove(err=>{
          if(err) return res.status(500).send({message:`Error al eliminar chat: ${err}`})
        })
        return res.status(500).send({message:`Error al actualizar chats del usuario: ${err}`})
      }
      console.log('Actualizados los chats del usuario: '+uid1)
    })

    let chat2 = {
            chatId:chatId,
            userId:uid1,
            foodId: foodId
          }

    User.findByIdAndUpdate(uid2,
            {$push: {"chats": chat2}},
            {safe: true, upsert: true, new : true},(err, userUpdated)=>{
            if(err){
              chatStored.remove(err=>{
                if(err) return res.status(500).send({message:`Error al eliminar chat: ${err}`})
              })
              return res.status(500).send({message:`Error al actualizar chats del usuario: ${err}`})
            }
            console.log('Actualizados los chats del usuario: '+uid2)
          })

    res.status(200).send({chat:chatStored})
  })


}

function deleteChat(req,res) {

  let chatId = req.params.chatId

  Chat.findById(chatId, (err,chat) => {
    if(err) return res.status(500).send({message:`Error al eliminar chat: ${err}`})
    if(!chat) return res.status(404).send({message:'El chat no existe'})

    let userId
    for (u of users){
      userId=u.userId
      User.findByIdAndUpdate(userId,
        {$pull: {"chats":{"chatId": chatId}}},{ safe: true},(err, userUpdated)=>{
        if(err) return res.status(500).send({message:`Error al actualizar chats del usuario: ${err}`})
      })

    }

    chat.remove(err=>{
      if(err) return res.status(500).send({message:`Error al eliminar chat: ${err}`})
      res.status(200).send({message:'El chat ha sido eliminado'})
    })
  })


}

function getChat(req, res){
  let chatId = req.params.chatId

  Chat.findById(chatId, (err, chat)=>{
    if(err) return res.status(500).send({message:`Error al realizar la peticion: ${err} `})
    if(!chat) return res.status(404).send({message:'El chat no existe'})

    res.status(200).send({ chat: chat})
  })

}

function newChatMessage(req,res) {

  let chatId = req.params.chatId
  let fromUserId = req.body.fromUserId
  let toUserId = req.body.toUserId
  let text = req.body.message

  let message = {
    userId:fromUserId,
    message:text,
    sendedDate:Date.now()
  }

  console.log(message)

  Chat.findByIdAndUpdate(chatId,
    {$push: {"messages": message}},
    {safe: true, upsert: true, new : true},(err, chatUpdated)=>{
    if(err) return res.status(500).send({message:`Error al actualizar chat: ${err}`})
    console.log(toUserId)

    User.findById(toUserId, (err, user)=>{
      if(err) return res.status(500).send({message:`Error al realizar la peticion: ${err} `})
      if(!user) return res.status(404).send({message:'El usuario no existe'})

      notifications.sendMessageNotification(user.tokenId, chatId, message, fromUserId)
    })

    res.status(200).send({message:'El mensaje ha sido enviado'})
  })

}

module.exports = {
  createChat,
  deleteChat,
  getChat,
  newChatMessage
}
