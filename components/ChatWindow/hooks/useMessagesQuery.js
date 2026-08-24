'use client'

import { useEffect, useState } from 'react'
import { createClient } from '@/lib/supabase/client'

export function useMessagesQuery(roomName) {
    const [data, setData] = useState([])

    useEffect(() => {
        const supabase = createClient()

        supabase
            .from('messages')
            .select('*')
            .eq('room_name', roomName)
            .order('created_at', { ascending: true })
            .then(({ data: rows, error }) => {
                if (error) {
                    console.error('Error loading messages:', error)
                    return
                }
                setData(
                    (rows ?? []).map((row) => ({
                        id: row.id,
                        content: row.content,
                        user: { name: row.username },
                        createdAt: row.created_at,
                    }))
                )
            })
    }, [roomName])

    return { data }
}
