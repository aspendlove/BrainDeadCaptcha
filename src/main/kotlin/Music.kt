
import java.io.File
import javax.sound.midi.MidiSystem
import javax.sound.midi.MidiUnavailableException
import javax.sound.sampled.AudioSystem

fun playSound(filePath: String) {
    val audioInputStream = AudioSystem.getAudioInputStream(File(filePath))
    val clip = AudioSystem.getClip()
    clip.open(audioInputStream)
    clip.start()
}

fun playNote(note: Int) {
    try {
        /* Create a new Synthesizer and open it. Most of
             * the methods you will want to use to expand on this
             * example can be found in the Java documentation here:
             * https://docs.oracle.com/javase/7/docs/api/javax/sound/midi/Synthesizer.html
        */
        val midiSynth = MidiSystem.getSynthesizer()
        midiSynth.open()

        // get and load default instrument and channel lists
        val instr = midiSynth.defaultSoundbank.instruments
        val mChannels = midiSynth.channels

        midiSynth.loadInstrument(instr[0]) // load an instrument

        mChannels[0].noteOn(note, 100) // On channel 0, play note number 60 with velocity 100
        try {
            Thread.sleep(2000) // wait time in milliseconds to control duration
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        mChannels[0].noteOff(note) // turn of the note
    } catch (e: MidiUnavailableException) {
        e.printStackTrace()
    }
}