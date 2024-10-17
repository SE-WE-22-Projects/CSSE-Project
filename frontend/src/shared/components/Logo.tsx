import { Box, styled, Typography } from '@mui/material'

const LogoText = styled(Typography)({
    fontSize: "1.2rem",
    textAlign: "start",
    display: "inline-block"
})

export const SiteLogo = () => {
    return (
        <Box sx={{ userSelect: "none", display: "inline-block" }}>
            <LogoText color='#DB5356'>Health</LogoText>
            <LogoText>Sphere</LogoText>
        </Box>
    )
}

export default SiteLogo