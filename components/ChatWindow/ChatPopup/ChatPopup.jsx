import {ChatWindow} from "../ChatWindow";
import styles from "../../Housing/CreateListingModal.module.css";
import {storeMessages} from "../../../lib/storemessages";
import {useMessagesQuery} from "../hooks/useMessagesQuery";
export const ChatPopup = ({ userId, onClose }) => {
    const roomName=`username-logged-in${userId}`
   const {data}= useMessagesQuery(roomName)
    const handleMessage = async (messages) => {
   console.log(messages);
      await storeMessages(messages,roomName)
    }
    return (<div className={styles.overlay} >
        <div onClick={onClose}> Close Chat</div>

        < ChatWindow roomName={roomName} username={userId} onMessage={handleMessage} messages={data} />
    </div>)
}
