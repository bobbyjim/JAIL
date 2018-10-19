(define login ()
(
   (equals 320 
      (length 
         (post 
            (config server)  "/pa/servlet/prov_user_login?"
            "USERNAME="      (config userid)
            "&PASSWD="       (config passwd)
         )
      )
   )
))
(cli)