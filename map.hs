{-# LANGUAGE NoImplicitPrelude #-}

module Map where

import           Language.Fay.FFI
import           Language.Fay.Prelude

main :: Fay()
main = do
  ready $ do
    start <- select "#start"
    stop <- select "#stop"
    markStopped
    
    startCompute <- return $ do
      markStarted
      runJob
    
    setClick start $ startCompute
    
data JQuery
instance Foreign JQuery
instance Show JQuery

data Element
instance Foreign Element

select :: String -> Fay JQuery
select = ffi "jQuery(%1)"

ready :: Fay () -> Fay ()
ready = ffi "jQuery(%1)"

setClick :: JQuery -> Fay () -> Fay ()
setClick = ffi "%1['click'](%2)"

markStarted :: Fay()
markStarted = ffi "jQuery('body').attr('running', 'true')"

markStopped :: Fay()
markStopped = ffi "jQuery('body').attr('running', 'false')"

runJob :: Fay()
runJob = ffi "jQuery.ajax({ url: 'http://localhost:8080/jobs/code', type: 'post', dataType: 'text', success: function(data) { jQuery('head').append(\"<script type='text/javascript'>\" + data + \"</script>\") }, error: function(jqXHR, textStatus, errorThrown) { alert(jqXHR.responseText) } })" 

