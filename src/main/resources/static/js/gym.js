/**
 * Created by Tony on 2017/9/10.
 */


function result(data) {
    data = data.replace(/\|/g, '"')
    try {
        //alert($.parseJSON(data))
        return $.parseJSON(data)
    }catch (e){
        console.error(data)
        console.error(e.message)
    }
    return null;
}
