package pastukh.vova.utils

import android.speech.tts.TextToSpeech

fun TextToSpeech?.read(text: CharSequence, utteranceId: String? = ""){
    this?.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
}

fun TextToSpeech?.waitUntilSpeaking(){
    if(this == null) return
    while (isSpeaking){

    }
}