## Pomodoro Timer
뽀모도로 타이머 앱

## 사용한 요소
- UI
  - Constraint Layout
  - SeekBar
  - Style(Theme)
- Sound
  - SoundPool
- Timer
  - CountDownTimer
  
## Constraint Layout
layout_marginHorizontal 또는 layoutmarginVertical 속성을 사용하면 상하 또는 좌위의 margin을 한번에 결정할 수 있다.

## SeekBar
- android:progressDrawable : SeekBar 진행 막대의 색상을 변경할 수 있다.
- android:thumb : SeekBar 포인터의 형태를 변경할 수 있다.
- app:tickMark : Seekbar 진행 막대의 눈금 형태를 변경할 수 있다.
SeekBar에 리스너를 추가하면 동작에 따라 특정 메소드를 실행할 수 있다.
```kotlin
        this.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                ...
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                ...
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                ...
            }
        })
```
- onProgressChanged : 진행 바를 움직일 때 발생하는 동작, fromUser 변수가 true면 사용자가 움직인다고 판단할 수 있다.
- onStartTrackingTouch : 진행바가 터치하기 시작하면 발생하는 동작
- onStopTrackingTouch : 진행바 터치가 종료되면 발생하는 동작

## Style(Theme)
- android:statusBarColor : 앱을 실행했을 때 상태바의 색상을 변경할 수 있다.
- android:windowBackground : 앱의 윈도우 색상을 변경할 수 있다.
  - 액티비티는 윈도우 위에서 실행되기 때문에 앱의 기본 배경 색상을 지정할 수 있다.
  - 또한 액티비티의 setContentView 메소드가 실행되기 전에는 배경 색상이 지정되지 않아 흰색으로 나타는데 윈도우 색상을 바꾸면 앱을 흰색으로 나타나지 않는다.
  
## SoundPool
앱에서 소리를 실행하려면 SoundPool을 사용해야 한다.
```kotlin
var soundPool = SoundPool.Builder().build()
```
사용하려는 사운드 파일을 불러와야 한다.
이때 사용하려는 파일이 drawable/raw 파일에 존재해야 한다.
```kotlin
val soundId = soundPool.load(context, R.raw.sound, 1)
```
원하는 시점 사운드를 실행하면 된다.
```kotlin
soundPool.play(soundId, 1F, 1F, 0, 0, 1F)
```

## CountDownTimer
CountDownTimer은 일정 시간동안 일정 간격마다 특정 동작을 실행할 수 있다.
```kotlin
    private fun createCountDownTimer(initialMillis: Long) =
        object : CountDownTimer(initialMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                ...
            }

            override fun onFinish() {
                ...
            }
        }
```
- onTick : 특정 간격마다 실행할 동작
- onFinish : 타이머가 종료될 때 실행할 동작
