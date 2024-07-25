"""
Adapted from D. Cooper, 
Worksheet 6b. Plugins and Scripting
(Accessed 27 October 2021)
"""

import texteditor

# Start-up logic
print("Now starting emoji script...")

# Define a Python class that implements the EmojiHandler API callback interface
class MyEmojiHandler(texteditor.EmojiHandler):
    def emoji(self, contents, caretPosition):
        # Ensure that we have at least three characters to analyse (":-)")
        if len(contents) > 3:
            if contents[caretPosition - 3] == ":" and \
            contents[caretPosition - 2] == "-" and \
            contents[caretPosition - 1] == ")":
                # Replace ':-)' with the smiley emoji
                contents = contents[:caretPosition - 3] + u'\U0001F60A' + contents[caretPosition:]
                # Notify the api to display the results
                api.displayEmoji(contents)

# Make a new object of this class
emojiHandler = MyEmojiHandler()

# Register it with the API
api.registerEmojiHandler(emojiHandler)
