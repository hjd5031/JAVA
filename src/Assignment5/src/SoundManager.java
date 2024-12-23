import javax.sound.sampled.*;

public class SoundManager {
    private static final int SONG_COUNT = 8; // 재생할 노래(사운드)의 개수
    private static Clip[] clips = new Clip[SONG_COUNT]; // Clip 배열

    // static 초기화 블록
    static {
        try {
            for (int i = 0; i < SONG_COUNT; i++) {
                // "sounds/song0.wav", "sounds/song1.wav" 경로에서 로드
                AudioInputStream ais = AudioSystem.getAudioInputStream(
                        SoundManager.class.getResource("/sounds/song" + i + ".wav")
                );
                clips[i] = AudioSystem.getClip();
                clips[i].open(ais);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 특정 노래 재생
    public static synchronized void play(int index) {
        if (index < 0 || index >= SONG_COUNT) return;

        Clip clip = clips[index];
        if (clip != null) {
            clip.stop(); // 재생 중인 경우 멈춤
            clip.setFramePosition(0); // 재생 위치를 처음으로
            clip.start(); // 재생
        }
    }
    // 특정 사운드 루프 재생
    public static synchronized void loop(int index, boolean continuous, int loopCount) {
        if (index < 0 || index >= SONG_COUNT) return;

        Clip clip = clips[index];
        if (clip != null) {
            clip.stop(); // 현재 재생 중인 클립을 멈춤
            clip.setFramePosition(0); // 재생 위치 초기화

            if (continuous) {
                clip.loop(Clip.LOOP_CONTINUOUSLY); // 무한 반복
            } else {
                clip.loop(loopCount); // 지정된 횟수만큼 반복
            }
        }
    }
    // 특정 노래 정지
    public static synchronized void stop(int index) {
        if (index < 0 || index >= SONG_COUNT) return;

        Clip clip = clips[index];
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}