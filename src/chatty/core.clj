;;; SPDX-FileCopyrightText: 2024 Imran Mustafa <imran@imranmustafa.net>
;;;
;;; SPDX-License-Identifier: GPL-3.0-or-later

(ns chatty.core
  (:gen-class))

(require '[clojure.string :as str])

(def brain
  {#"(.*\s)?are you(\s.*)?" ["Why are you interested in whether I am -2 or not?" "Would you prefer if I weren't -2?" "Perhaps I am -2 in your fantasies." "Do you sometimes think I am -2?"]
   #"(.*\s)?are(\s.*)?" ["Possibly they are -2." "What if they were not -2?" "Would you like it if they were not -2?" "Did you think they might not be -2?"]

   #"\W?(sorry|apologies)\s" ["Please don't apologise." "Apologies are not necessary." "I've told you that apologies are not required."]

   #"(.*\s)?i remember(\s.*)?" ["Do you often think of -2?" "Does thinking of -2 bring anything else to mind?" "What else do you recollect?" "Why do you recollect -2 just now?" "What in the present situation reminds you of -2?" "What is the connection between me and -2?"]

   #"(.*\s)?do you remember(\s.*)?" ["Did you think I would forget -2?" "Why do you think I should recall -2 now?" "What about -2?" "You mentioned -2?"]

   #"(.*\s)?if(\s.*)?" ["Do you think its likely that -2?" "Do you wish that -2?" "What do you know about -2?" "Really, if -2?"]

   #"(.*\s)?i dreamed(\s.*)?" ["Really, -2?" "Have you ever fantasized -2 while you were awake?" "Have you ever dreamed -2 before?"]
   #"dream" ["What does that dream suggest to you?" "Do you dream often?" "What persons appear in your dreams?" "Do you believe that dreams have something to do with your problems?"]

   #"\W?perhaps\s" ["You don't seem quite certain." "Why the uncertain tone?" "Can't you be more positive?" "You aren't sure?" "Don't you know?"]

   #"\W?computer\s" ["Do computers worry you?" "Why do you mention computers?" "What do you think machines have to do with your problem?" "Don't you think computers can help people?" "What about machines worrys you?" "What do you think about machines?"]

   #"(.*\s)?am i(\s.*)?" ["Do you believe you are -2?" "Would you want to be -2?" "Do you wish I would tell you you are -2?" "What would it mean if you were -2?"]
   #"am" ["Why do you say 'am'?" "I don't understand that."]

   #"(.*\s)?your(\s.*)?" ["Why are you concerned over my -2?" "What about your own -2?" "Are you worried about someone else's -2?" "Really, my -2?"]

   #"(.*\s)?was i(\s.*)?" ["What if you were -2?" "Do you think you were -2?" "Were you -2?" "What would it mean if you were -2?" "What does -2 suggest to you?"]
   #"(.*\s)?i was(\s.*)?" ["Were you really?" "Why do you tell me you were -2 now?" "Perhaps I already know you were -2."]
   #"(.*\s)?was you(\s.*)?" ["Would you like to believe I was -2?" "What suggests that I was -2?" "What do you think?" "Perhaps I was -2." "What if I had been -2?"]

   #"(.*\s)?i (desire)(\s.*)?" ["hat would it mean to you if you got -3?" "Why do you want -3?" "Suppose you got -3 soon?" "What if you never got -3?" "What would getting -3 mean to you?" "What does wanting -3 have to do with this discussion?"]
   #"(.*\s)?i am(\s.*\s)?(sad)(\s.*)?" ["I am sorry to hear that you are -3." "Do you think that coming here will help you not to be -3?" "I'm sure it's not pleasant to be -3." "Can you explain what made you -3?"]
   #"(.*\s)?i am(\s.*\s)?(happy)(\s.*)?" ["How have I helped you to be -3?" "Has your treatment made you -3?" "What makes you -3 just now?" "Can you explan why you are suddenly -3?"]
   #"(.*\s)?i (belief)(\s.*\s)? i (\s.*)?" ["Do you really think so?" "But you are not sure you -3." "Do you really doubt you -3?"]
   #"(.*\s)?i am(\s.*)?" ["Is it because you are -2 that you came to me?" "How long have you been -2?" "Do you believe it is normal to be -2?" "Do you enjoy being -2?"]
   #"(.*\s)?i (cannot)(\s.*)?" ["How do you think that you can't -3?" "Have you tried?" "Perhaps you could -3 now." "Do you really want to be able to -3?"]
   #"(.*\s)?i don't(\s.*)?" ["Don't you really -2?" "Why don't you -2?" "Do you wish to be able to -2?" "Does that trouble you?"]
   #"(.*\s)?do i feel(\s.*)?" ["Tell me more about such feelings." "Do you often feel -2?" "Do you enjoy feeling -2?" "Of what does feeling -2 remind you?"]
   #"(.*\s)?i(\s.*\s)?you(\s.*)?" ["Perhaps in your fantasies we -2 each other." "Do you wish to -2 me?" "You seem to need to -2 me." "Do you -2 anyone else?"]

   #"(.*\s)?you are(\s.*)?" ["What makes you think I am -2?" "Does it please you to believe I am -2?" "Do you sometimes wish you were -2?" "Perhaps you would like to be -2."]
   #"(.*\s)?you( .* )me(\s.*)?" ["Why do you think I -2 you?" "You like to think I -2 you -- don't you?" "What makes you think I -2 you?" "Really, I -2 you?" "Do you wish to believe I -2 you?" "Suppose I did -2 you -- what would that mean?" "Does someone else believe I -2 you?"]
   #"(.*\s)?you(\s.*)?" ["We were discussing you -- not me." "Oh, I -2?" "You're not really talking about me -- are you?" "What are your feelings now?"]

   #"(.*\s)?my( .*)(sis|sister|bro|brother|sib|sibling|mom|mother|dad|farther|uncle|aunt)(\s.*)?" ["Tell me more about your family." "Who else in your family -4?" "Your -3?" "What else comes to mind when you think of your -3?"]
   #"(.*\s)?my(\s.*)?" ["Your -2?" "Why do you say your -2?" "Does that suggest anything else which belongs to you?" "Is it important that your -2?"]

   #"(.*\s)?can you(\s.*)?" ["You believe I can -2 don't you?" "You want me to be able to -2." "Perhaps you would like to be able to -2 yourself."]
   #"(.*\s)?can i(\s.*)?" ["Whether or not you can -2 depends on you more than me." "Do you want to be able to -2?" "Perhaps you don't want to -2."]

   #"(.*\s)?why don't you(\s.*)?" ["Do you believe I don't -2?" "Perhaps I will -2 in good time." "Should you -2 yourself?" "You want me to -2?"]

   #"(.*\s)?why can't i(\s.*)?" ["Do you think you should be able to -2?" "Do you want to be able to -2?" "Do you believe this will help you to -2?" "Have you any idea why you can't -2?"]

   #"(.*\s)?(everybody|nobody|none)(\s.*)?" ["Realy, -2?" "Surely not -2." "Can you think of anyone in particular?" "Who, for example?" "Are you thinking of a very special person?" "Who, may I ask?" "Someone special perhaps?" "You have a particular person in mind, don't you?" "Who do you think you're talking about?"]

   #"\W?yes\s?" ["You seem to be quite positive." "You are sure." "I see." "I understand."]
   #"\W?no\s?" ["Are you saying no just to be negative?" "You are being a bit negative." "Why not?" "Why 'no'?"]

   #"\W?(what|why)\s?" ["Why do you ask?" "Does that question interest you?" "What is it you really wanted to know?" "Are such questions much on your mind?" "What answer would please you most?" "What do you think?" "What comes to mind when you ask that?" "Have you asked such questions before?
" "Have you asked anyone else?"]

   #"\W?because\s?" ["Is that the real reason?" "Don't any other reasons come to mind?" "Does that reason seem to explain anything else?" "What other reasons might there be?"]

   #"\W?always\s?" ["Can you think of a specific example?" "When?" "What incident are you thinking of?" "Really, always?"]

   #"\W?alike\s?" ["In what way?" "What resemblence do you see?" "What does that similarity suggest to you?" "What other connections do you see?" "What do you suppose that resemblence means?" "What is the connection, do you suppose?" "Could here really be some connection?" "How?"]})

(def fail ["I'm not sure I understand you fully." "Please go on." "What does that suggest to you?" "Do you feel strongly about discussing such things?" "I don't understand?"])

(def greeting ["How do you do." "Please tell me your problem."])

(def prefixes [#"\W?dont\s" " don't "
               #"\W?cant\s" " can't "
               #"\W?wont\s" " won't "
               #"\W?recollect\s" " remember "
               #"\W?dreamt\s" " dreamed "
               #"\W?dreams\s" " dream "
               #"\W?maybe\s" " perhaps "
               #"\W?how\s" " what "
               #"\W?when\s" " what "
               #"\W?certainly\s" " yes "
               #"\W?machine\s" " computer "
               #"\W?computers\s" " computer "
               #"\W?were\s" " was "
               #"\W?you're\s" " you are "
               #"\W?i'm\s" " i am "
               #"\W?same\s" " alike "])

(def postfixes [#"\s{2,}" " "
                #"\W?am\s" " are "
                #"\W?your\s" " my "
                #"\W?me\s" " you "
                #"\W?myself\s" " yourself "
                #"\W?yourself\s" " myself "
                #"\W?i\s" " you "
                #"\W?you\s" " I "
                #"\W?my\s" " your "
                #"\W?i'm\s" " you are "])

(defn postprocces [content fixes]
  (let [replacement-list (partition 2 fixes)]
    (reduce #(apply str/replace %1 %2) content replacement-list)))

(defn sanitize
  "Take the user input and cleans it up"
  [dirty-input]
  (postprocces (str/lower-case (str/trim dirty-input)) prefixes))

(defn user-ask
  "asks user for input."
  []
  (print "user: ")
  (flush)
  (sanitize (read-line)))

(defn populate
  "Fill template with user context."
  ([template context]
   (if (= (count context) 1)
     template 
     (populate template (rest context) 2)))
  ([template context num]
   (if (not= (count context) 0)
     (let [tstr (format "-%d" num)
           pat (re-pattern tstr)]
       (recur (str/replace template pat (postprocces (first context) postfixes)) (rest context) (inc num)))
     template)))

(defn analyze
  "Query brain for best fitting response."
  [prompt]
  (for [[pattern response] brain]
    (let [match (re-find pattern prompt)]
      (if match
        (let [template (rand-nth response)
              extract (rest match)]
          (populate template (map str extract)))))))

(defn sanitycheck
  "make sure that something got through"
  [lst]
  (let
   [pos (remove nil? lst)]
    (if (seq pos) pos [(rand-nth fail)])))

(def bar (apply str (repeat 80 "-")))

(def banner
  (str
   "Welcome to chatty!\n"
   "a CLI chatbot based off of ELIZA\n"
   "type \"quit()\" to leave the program\n"
   bar))

(def farewell
  (str
   bar
   "\nThanks for talking with chatty!\n"
   "Hope to see you again."))

(defn -main
  "chatty's basic loop."
  [& args]
  (println banner)

  (println "chatty:" (rand-nth greeting))
  (loop [prompt (user-ask)]
    (if (= prompt "quit()")
      (println farewell)
      (do (println "chatty:" (first (sanitycheck (analyze prompt))))
          (recur (user-ask))))))
