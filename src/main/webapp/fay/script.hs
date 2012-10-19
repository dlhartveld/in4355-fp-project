{-# LANGUAGE EmptyDataDecls    #-}
{-# LANGUAGE NoImplicitPrelude #-}

module Home (main) where

import           Language.Fay.FFI
import           Language.Fay.Prelude


-- | Main entry point.
main :: Fay ()
main = do
  ready (wrap thedocument) $ do
    attachClick


-- click

attachClick :: Fay()
attachClick = do
  startbutton <- query "#start-button" >>= getFirst  
  progress <- query "#progress-bar" >>= getFirst
  unicorn <- query "#unicorn" >>= getFirst
  animated <- query "#animated_div" >>= getFirst
  
  togglestartstop <- return $ do
    active <- getIs progress ".active"
    if active
      then do removeClass progress "active"
              removeClass animated "active"
              setText startbutton "start"
              stop
      else do addClass progress "active"
              addClass animated "active"
              setText startbutton "stop"
              start
                 
  setClick startbutton togglestartstop


start :: Fay()
start = do
	print "started"
	
stop :: Fay()
stop = do
	print "stopped"

-- DOM

data Element
instance Foreign Element
instance Show Element

-- | The document.
thedocument :: Element
thedocument = ffi "window.document"

-- | Get the size of the given jquery array.
getTagName :: Element -> Fay String
getTagName = ffi "%1['tagName']"

-- | Show message box
alert :: String -> Fay ()
alert = ffi "window.alert(%1)"

print :: String -> Fay ()
print = ffi "window.console.log(%1)"



--------------------------------------------------------------------------------
-- jQuery

data JQuery
instance Foreign JQuery
instance Show JQuery

-- | Make a jQuery object out of an element.
wrap :: Element -> JQuery
wrap = ffi "window['jQuery'](%1)"

-- | Bind a handler for when the element is ready.
ready :: JQuery -> Fay () -> Fay ()
ready = ffi "%1['ready'](%2)"

-- | Bind a handler for when the element is ready.
each :: JQuery -> (Double -> Element -> Fay Bool) -> Fay ()
each = ffi "%1['each'](%2)"

-- | Query for elements.
query :: String -> Fay JQuery
query = ffi "window['jQuery'](%1)"

-- | Set the text of the given object.
setText :: JQuery -> String -> Fay ()
setText = ffi "%1['text'](%2)"

-- | Set the html of the given object.
setHtml :: JQuery -> String -> Fay ()
setHtml = ffi "%1['html'](%2)"

-- | Set the text of the given object.
attr :: JQuery -> String -> String -> Fay ()
attr = ffi "%1['attr'](%2,%3)"

-- | Set the click of the given object.
setClick :: JQuery -> Fay () -> Fay ()
setClick = ffi "%1['click'](%2)"

-- | Toggle the visibility of an element, faded.
fadeToggle :: JQuery -> Fay () -> Fay ()
fadeToggle = ffi "%1['fadeToggle'](%2)"

-- | Hide an element.
hide :: JQuery -> Fay ()
hide = ffi "%1['hide']()"

-- | Add a class to the given object.
addClass :: JQuery -> String -> Fay ()
addClass = ffi "%1['addClass'](%2)"

-- | Remove a class from the given object.
removeClass :: JQuery -> String -> Fay ()
removeClass = ffi "%1['removeClass'](%2)"

-- | indicates if the given object has the class.
hasClass :: JQuery -> String -> Fay Bool
hasClass = ffi "%1['hasClass'](%2)"

-- | Swap the given classes on the object.
swapClasses :: JQuery -> String -> String -> Fay ()
swapClasses obj addme removeme = do
  addClass obj addme
  removeClass obj removeme

-- | Get the text of the given object.
getText :: JQuery -> Fay String
getText = ffi "%1['text']()"

-- | Get the text of the given object.
getIs :: JQuery -> String -> Fay Bool
getIs = ffi "%1['is'](%2)"

-- | Get the size of the given jquery array.
getSize :: JQuery -> Fay Double
getSize = ffi "%1['length']"

-- | Get the next of the given object.
getNext :: JQuery -> Fay JQuery
getNext = ffi "%1['next']()"

-- | Get the first of the given object.
getFirst :: JQuery -> Fay JQuery
getFirst = ffi "%1['first']()"

-- | Get the find of the given object.
getFind :: JQuery -> String -> Fay JQuery
getFind = ffi "%1['find'](%2)"

-- | Prepend an element to this one.
prepend :: JQuery -> JQuery -> Fay JQuery
prepend = ffi "%1['prepend'](%2)"

-- | Append an element /after/ this one.
after :: JQuery -> JQuery -> Fay JQuery
after = ffi "%2['after'](%1)"

-- | Append an element to this one.
append :: JQuery -> JQuery -> Fay JQuery
append = ffi "%1['append'](%2)"

-- | Append this to an element.
appendTo :: JQuery -> JQuery -> Fay JQuery
appendTo = ffi "%2['appendTo'](%1)"

-- | Make a new element.
makeElement :: String -> Fay JQuery
makeElement = ffi "window['jQuery'](%1)"

-- | Get the width of the given object.
getWidth :: JQuery -> Fay Double
getWidth = ffi "%1['width']()"
    