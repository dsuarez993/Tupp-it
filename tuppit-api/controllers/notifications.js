'use strict'

const admin = require("firebase-admin");
const serviceAccount = require("../service_account/tuppit-fc5cf-firebase-adminsdk-r9obb-0a5befd6ce.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://tuppit-fc5cf.firebaseio.com"
});


function sendMessageNotification(tokenId, chatId, message, fromUser) {
  console.log("tokenId::"+tokenId)
  console.log("chatId::"+chatId)
  console.log(message)
  console.log("fromUser::"+fromUser)
  var payload = {
    notification: {
        title: "Mensaje",
        body: message.message
      },
    data: {
      chatId: chatId,
      message: message.message,
      fromUser: fromUser
    }
  };

  // Send a message to the device corresponding to the provided
  // registration token.
  admin.messaging().sendToDevice(tokenId, payload)
    .then(function(response) {
      // See the MessagingDevicesResponse reference documentation for
      // the contents of response.
      console.log("Successfully sent message:", response);
    })
    .catch(function(error) {
      console.log("Error sending message:", error);
    });

}

module.exports = {
  sendMessageNotification
}
