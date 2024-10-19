import { Stack, Avatar, Box, Typography } from '@mui/material'
import OptionsMenu from './OptionsMenu'
import { useNavigate } from 'react-router-dom';
import { useEffect } from 'react';
import { useUser } from '../../../components/User';

const UserCard = () => {
    const user = useUser();
    const navigate = useNavigate();

    useEffect(() => {
        if (!user.loggedIn || !user.user?.isStaff) {
            navigate("/login");
        }
    }, [user])


    return (
        <Stack
            direction="row"
            sx={{
                p: 2,
                gap: 1,
                alignItems: 'center',
                borderTop: '1px solid',
                borderColor: 'divider',
            }}
        >
            <Avatar
                sizes="small"
                alt={user.user?.username}
                src="/static/images/avatar/7.jpg"
                sx={{ width: 36, height: 36 }}
            />
            <Box sx={{ mr: 'auto' }}>
                <Typography variant="body2" sx={{ fontWeight: 500, lineHeight: '16px' }}>
                    {user.user?.username}
                </Typography>
                <Typography variant="caption" sx={{ color: 'text.secondary' }}>
                    {user.user?.email}
                </Typography>
            </Box>
            <OptionsMenu />
        </Stack>
    )
}

export default UserCard