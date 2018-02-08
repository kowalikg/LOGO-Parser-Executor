### Installing

```
git clone https://gitlab.com/agh-to-2017/wt-1250-zolwie-ninja.git
```

### Running

```
cd wt-1250-zolwie-ninja
./gradlew run           // or just gradlew run on Windows Command Prompt
```

### Usage

##### Basic commands:

```
np <x>
```
move forward by x steps

```
ws <x>
```
move backwards by x steps

```
pw <x>
```
rotate by x degrees to the right

```
lw <x>
```
rotate by x degrees to the left

```
pdn
```
lifts the tortoise off the board(so he doesn't leave a trail

```
ops
```
lowers the tortoise on the board

##### Loops:
```
powtorz <x> [<some valid expression>]
```
a loop that will execute the expression in the square brackets
x number of times

###### Example:
```
powtorz 4 [np 50 pw 90]
```
This snippet will generate a square with a side of 50 units

##### Custom procedures:
You can define your own procedures in the following way:
```
oto functionname (arg1, arg2) {<valid expresseion>}
```

###### Example:
```
oto kwadrat (bok) {powtorz 4 [np bok pw 90]}
```

You can call your procedure like this:
```
kwadrat 30
```
This call will generate a square with a side of 30 units