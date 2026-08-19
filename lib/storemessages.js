import { createClient } from '@/lib/supabase/client'

export async function storeMessages(messages,roomName) {
    const supabase = createClient()

    const { error } = await supabase.from('messages').upsert(
        messages.map((message) => ({
            id: message.id,
            room_name: roomName,
            content: message.content,
            username: message.user.name,
            created_at: message.createdAt,
        }))
    )

    if (error) console.error('Error storing messages:', error)
}