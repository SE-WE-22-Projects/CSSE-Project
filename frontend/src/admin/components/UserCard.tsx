import { Stack, Avatar, Box, Typography } from '@mui/material'
import OptionsMenu from './OptionsMenu'
import { getUser } from '../../util/user'

const UserCard = () => {
    const user = getUser()!;

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
                alt={user.username}
                src="/static/images/avatar/7.jpg"
                sx={{ width: 36, height: 36 }}
            />
            <Box sx={{ mr: 'auto' }}>
                <Typography variant="body2" sx={{ fontWeight: 500, lineHeight: '16px' }}>
                    {user.username}
                </Typography>
                <Typography variant="caption" sx={{ color: 'text.secondary' }}>
                    {user.email}
                </Typography>
            </Box>
            <OptionsMenu />
        </Stack>
    )
}

export default UserCard