# Concurrent WordGame makefile
# Zaidh Imran
# 07/09/2021

JAVAC=/usr/bin/javac
.SUFFIXES: .java .class
SRCDIR=src
BINDIR=bin

$(BINDIR)/%.class:$(SRCDIR)/%.java
	$(JAVAC) -d $(BINDIR)/ -cp $(BINDIR) $<

CLASSES=  WordDictionary.class WordRecord.class Score.class WordPanel.class WordApp.class
CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)

default: $(CLASS_FILES)

clean:
	rm $(BINDIR)/*.class
v1 =
v2 =
v3 =
runwordgame: $(CLASS_FILES)
	java -cp $(BINDIR) WordApp $(v1) $(v2) $(v3)
