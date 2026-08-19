"use client"
import {ChatPopup} from "../ChatWindow/ChatPopup/ChatPopup";
import styles from "../../app/(student)/housing/[id]/page.module.css";
import {useState} from "react";

export const ChatButton = ({authorName}) => {
    const [modalOpen, setModalOpen] = useState(false);
    return (<div> {
        modalOpen && (
            <ChatPopup userId={authorName} onClose={() => setModalOpen(false)} />
        )
    }
        <button type="button" className={`btn btn-outline ${styles.contactBtn}`} onClick={()=>setModalOpen(true)}>
            Message {authorName}
        </button>
    </div>)
}