hangman_pics=[' ','''
==========''', '''
 |
 |
 |
 |
 |
==========''', '''
 +---+
 |
 |
 |
 |
 |
==========''', '''
 +---+
 |    |
 |
 |
 |
 |
==========''', '''
 +---+
 |    |
 |    O
 |
 |
 |
==========''', '''
 +---+
 |    |
 |    O
 |    |
 |
 |
==========''', '''
 +---+
 |    |
 |    O
 |   /|
 |
 |
==========''', '''
 +---+
 |    |
 |    O
 |   /|\ 
 |
 |
==========''', '''
 +---+
 |    |
 |    O
 |   /|\ 
 |   /
 |
==========''', '''
 +---+
 |    |
 |    O
 |   /|\ 
 |   / \ 
 |
==========''']
import random
import sys

def chooseword(a):
# Choose a random word in the file and make sure the mode player want to choose.
    if a=='easy game':
        file=open("easyWORD","r")
        line=file.readline()
        list=line.split( )
        return random.choice(list)
    if a=='hard game':
        file=open("hardWORD","r")
        line=file.readline()
        list=line.split( )
        return random.choice(list)
    if a=='Exit':
        sys.exit(0)
    else:
        print('Make sure you print easy game or hard game')
        sys.exit(0)

def display(missedletters, correctletters, secretword):
#show the picture of hangman
    print(hangman_pics[len(missedletters)])
    print()

    print('Incorrect answers:', end=' ')
    for letter in missedletters:
        print(letter, end=' ')
    print()

    blanks = '_' * len(secretword)

    for i in range(len(secretword)):
#replace blanks with correctly guessed letters
        if secretword[i] in correctletters:
            blanks = blanks[:i] + secretword[i] + blanks[i + 1:]

#show the secret word with spaces in betweeen each letter
    for letter in blanks:
        print(letter, end=' ')
    print()
def checktheletter(alreadyguessed):
# makes sure the player enter a single letter and not something else
    while True:
        guess = input("Guess the word:")
        guess = guess.lower()
        if len(guess) != 1:
            print('Please enter a single letter.')
        elif guess in alreadyguessed:
            print('you have already guessed that letter. Choose again.')
        elif guess not in 'abcdefghijklmnopqrstuvwxyz':
            print('Please enter a LETTER.')
        else:
            return guess


def playagain():
#Let this game can be play again when player finish playing
    print('Do you want to play again ? (yes or no)')
    return input().lower().startswith('y')

#Main game
print('H A N G M A N')
print('Welcome to hangman game!')
print("1.Easy Game")
print("2.Hard Game")
print('3.Exit')
level = input("Enter your choice:").lower()

missedLetters = ' '
correctLetters = ' '
secretword = chooseword(level)
gameisdone = False

while True:
    display(missedLetters, correctLetters, secretword)

    guess = checktheletter(missedLetters + correctLetters)

    if guess in secretword:
        correctLetters = correctLetters + guess

        foundAllLetters = True
        for i in range(len(secretword)):
            if secretword[i] not in correctLetters:
                foundAllLetters = False
                break

        if foundAllLetters == True:
            print('Yes! The secret word is "' + secretword + '"! You have won!')

            gameisdone = True
    else:
        missedLetters = missedLetters + guess

        # Check if player has guessed too many times and lost.
        if len(missedLetters) == len(hangman_pics) - 1:
            display(missedLetters, correctLetters, secretword)
            print('You lose!The word was '+ secretword + ' " ')
            gameisdone = True

    # Ask the player if they want to play again(but only if the game is done.)
    if gameisdone==True:
        if playagain():
            print('Welcome to hangman game!')
            print("1.Easy Game")
            print("2.Hard Game")
            print('3.Exit')
            level = input("Enter your choice:")
            missedLetters = ''
            correctLetters = ''
            gameisdone = False
            secretword = chooseword((level))
        else:
            break






