import { Stack, Avatar, Box, Typography } from '@mui/material'
import OptionsMenu from './OptionsMenu'
import { useNavigate } from 'react-router-dom';
import { useEffect } from 'react';
import { useUser } from '../../../components/User';

const UserCard = ({ small }: { small?: boolean }) => {
    const user = useUser();
    const navigate = useNavigate();

    useEffect(() => {
        if (!user.loggedIn || !user.isStaff) {
            navigate("/");
        }
    }, [user])


    return (
        <Stack
            direction="row"
            sx={{
                p: small ? 0 : 2,
                gap: 1,
                alignItems: 'center',
                borderTop: small ? 'none' : '1px solid',
                borderColor: 'divider',
            }}
        >
            <Avatar
                sizes="small"
                alt={user.token?.username}
                src="/static/images/avatar/7.jpg"
                sx={{ width: small ? 30 : 36, height: small ? 30 : 36 }}
            />
            <Box sx={{ mr: 'auto' }}>
                <Typography variant="body2" sx={{ fontWeight: 500, lineHeight: '16px', color: 'text.primary' }}>
                    {user.token?.username}
                </Typography>
                {small ? null :
                    <Typography variant="caption" sx={{ color: 'text.secondary' }}>
                        {user.token?.email}
                    </Typography>
                }
            </Box>
            <OptionsMenu />
        </Stack>
    )
}

export default UserCard