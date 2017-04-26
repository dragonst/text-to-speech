package speechtest.speechandbuffer.component;

public class ComponentSM extends no.ntnu.item.arctis.runtime.IStateMachine {

    private speechtest.speechandbuffer.component.Component b000;
    private no.ntnu.item.arctis.library.speech.speech.Speech b001;
    private com.bitreactive.library.buffering.buffereagersimple.BufferEagerSimple<java.lang.String> b002;
    private enum No_ntnu_item_arctis_library_speech_Speech {_IDLE, ACTIVE, SPEAKING};
    private enum Com_bitreactive_library_buffering_BufferEagerSimple {_IDLE, ACTIVE, FIRSTITEMADDED};
    private No_ntnu_item_arctis_library_speech_Speech b001_state;
    private Com_bitreactive_library_buffering_BufferEagerSimple b002_state;
    private enum Com_bitreactive_library_buffering_BufferEagerSimple_Stm {_IDLE, ACTIVE, FIRSTITEMADDED, ISBUFFEREMPTY};
    private Com_bitreactive_library_buffering_BufferEagerSimple_Stm b002_stm_state;
    private no.ntnu.item.arctis.runtime.IStateMachine.Timer _Speech_and_Buffer_t1;
    private no.ntnu.item.arctis.runtime.IStateMachine.Break _c1_Buffer_Eager_Simple_t0;
    private boolean b001_SPEECH_READY_r0;
    private boolean b001_SPEECH_FAILED_r1;

    public ComponentSM(no.ntnu.item.arctis.runtime.Scheduler scheduler) {
        super(scheduler);
    }

    public int fireInitial() {
        b000 = new speechtest.speechandbuffer.component.Component();
        b000.setBlockID("b000", sessionID);
        b000.setRuntime(scheduler.getRuntime());
        b001 = new no.ntnu.item.arctis.library.speech.speech.Speech();
        b001.setBlockID("b001", sessionID);
        b001.setRuntime(scheduler.getRuntime());
        b002 = new com.bitreactive.library.buffering.buffereagersimple.BufferEagerSimple<java.lang.String>(true);
        b002.setBlockID("b002", sessionID);
        b002.setRuntime(scheduler.getRuntime());
        b001_state = No_ntnu_item_arctis_library_speech_Speech._IDLE;
        b002_state = Com_bitreactive_library_buffering_BufferEagerSimple._IDLE;
        b002_stm_state = Com_bitreactive_library_buffering_BufferEagerSimple_Stm._IDLE;
        _Speech_and_Buffer_t1 = new no.ntnu.item.arctis.runtime.IStateMachine.Timer(500, "_Speech_and_Buffer_t1", this);
        _c1_Buffer_Eager_Simple_t0 = new no.ntnu.item.arctis.runtime.IStateMachine.Break("_c1_Buffer_Eager_Simple_t0", this);
        b000.getNotepadText();
        java.lang.String b000_o1 = b000.pronounceSentence();
        com.sun.speech.freetts.Voice b001_o2 = b001.initVoice();
        if (b001_o2 == null) {
            b002.add = b000_o1;
            b002.addToBuffer();
            scheduler.startBreak(_c1_Buffer_Eager_Simple_t0);
            b002_stm_state = Com_bitreactive_library_buffering_BufferEagerSimple_Stm.FIRSTITEMADDED;
            scheduler.stopTimer(_Speech_and_Buffer_t1);
            b001_SPEECH_READY_r0 = false;
            scheduler.flushEventQueue(sessionID, "SPEECH_READY_b001");
            b001_SPEECH_FAILED_r1 = false;
            scheduler.flushEventQueue(sessionID, "SPEECH_FAILED_b001");
            b002_state = Com_bitreactive_library_buffering_BufferEagerSimple.FIRSTITEMADDED;
            // step e83b4;
            return CONSUME_SIGNAL;
        } else {
            b001.myVoice = b001_o2;
            b002.add = b000_o1;
            b002.addToBuffer();
            scheduler.startBreak(_c1_Buffer_Eager_Simple_t0);
            b002_stm_state = Com_bitreactive_library_buffering_BufferEagerSimple_Stm.FIRSTITEMADDED;
            b001_state = No_ntnu_item_arctis_library_speech_Speech.ACTIVE;
            b002_state = Com_bitreactive_library_buffering_BufferEagerSimple.FIRSTITEMADDED;
            // step 50734;
            return CONSUME_SIGNAL;
        }
    }

    public int fireTimer(java.lang.String _timerID) {
        if (_timerID.equals("_Speech_and_Buffer_t1")) {
            return handleTimer__Speech_and_Buffer_t1();
        } else if (_timerID.equals("_c1_Buffer_Eager_Simple_t0")) {
            return handleTimer__c1_Buffer_Eager_Simple_t0();
        }
        return DISCARD_SIGNAL;
    }

    public int fire(java.lang.Object _sender, java.lang.Object receiverSessionID, java.lang.String _signalID, java.lang.Object _data) {
        if (_signalID.equals("SPEECH_READY_b001")) {
            return handleEvent_SPEECH_READY_b001(_sender, receiverSessionID, _data);
        } else if (_signalID.equals("SPEECH_FAILED_b001")) {
            return handleEvent_SPEECH_FAILED_b001(_sender, receiverSessionID, _data);
        }
        return TRIGGER_UNKNOWN;
    }

    private int handleTimer__Speech_and_Buffer_t1() {
        if (b002.isEmpty()) {
            b002_stm_state = Com_bitreactive_library_buffering_BufferEagerSimple_Stm._IDLE;
            scheduler.stopBreak(_c1_Buffer_Eager_Simple_t0);
            b001.terminate();
            // step d3de7;
            return TERMINATE_MACHINE;
        } else {
            b002.getFromBuffer();
            b001.speak(b002.out);
            b001_SPEECH_READY_r0 = true;
            b001_SPEECH_FAILED_r1 = true;
            b001_state = No_ntnu_item_arctis_library_speech_Speech.SPEAKING;
            // step 6f5da;
            return CONSUME_SIGNAL;
        }
    }

    private int handleTimer__c1_Buffer_Eager_Simple_t0() {
        if (b002_state == Com_bitreactive_library_buffering_BufferEagerSimple.FIRSTITEMADDED) {
            if (b002_stm_state == Com_bitreactive_library_buffering_BufferEagerSimple_Stm.FIRSTITEMADDED) {
                b002.getFromBuffer();
                b002_stm_state = Com_bitreactive_library_buffering_BufferEagerSimple_Stm.ACTIVE;
                b001.speak(b002.out);
                b001_SPEECH_READY_r0 = true;
                b001_SPEECH_FAILED_r1 = true;
                b001_state = No_ntnu_item_arctis_library_speech_Speech.SPEAKING;
                b002_state = Com_bitreactive_library_buffering_BufferEagerSimple.ACTIVE;
                // step 4f3e3;
                return CONSUME_SIGNAL;
            } else {
                return DISCARD_SIGNAL;
            }
        } else {
            return DISCARD_SIGNAL;
        }
    }

    private int handleEvent_SPEECH_READY_b001(java.lang.Object _sender, java.lang.Object receiverSessionID, java.lang.Object _data) {
        if (b001_SPEECH_READY_r0) {
            if (b001_state == No_ntnu_item_arctis_library_speech_Speech.ACTIVE) {
                b001_SPEECH_READY_r0 = false;
                // step 4647d;
                return CONSUME_SIGNAL;
            } else if (b001_state == No_ntnu_item_arctis_library_speech_Speech.SPEAKING) {
                java.lang.String b000_o1 = b000.pronounceSentence();
                b002.add = b000_o1;
                b002.addToBuffer();
                scheduler.startOrRestartTimer(_Speech_and_Buffer_t1);
                b001_SPEECH_READY_r0 = false;
                b001_state = No_ntnu_item_arctis_library_speech_Speech.ACTIVE;
                // step 048cf;
                return CONSUME_SIGNAL;
            } else {
                return DISCARD_SIGNAL;
            }
        } else {
            return DISCARD_SIGNAL;
        }
    }

    private int handleEvent_SPEECH_FAILED_b001(java.lang.Object _sender, java.lang.Object receiverSessionID, java.lang.Object _data) {
        if (b001_SPEECH_FAILED_r1) {
            if (b001_state == No_ntnu_item_arctis_library_speech_Speech.ACTIVE) {
                b001_SPEECH_FAILED_r1 = false;
                // step 21664;
                return CONSUME_SIGNAL;
            } else if (b001_state == No_ntnu_item_arctis_library_speech_Speech.SPEAKING) {
                java.lang.String b000_o1 = b000.pronounceSentence();
                b002.add = b000_o1;
                b002.addToBuffer();
                scheduler.startOrRestartTimer(_Speech_and_Buffer_t1);
                b001_SPEECH_FAILED_r1 = false;
                b001_state = No_ntnu_item_arctis_library_speech_Speech.ACTIVE;
                // step e2c61;
                return CONSUME_SIGNAL;
            } else {
                return DISCARD_SIGNAL;
            }
        } else {
            return DISCARD_SIGNAL;
        }
    }
}
